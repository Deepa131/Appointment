package com.example.newproject

import com.example.newproject.repository.SignUpRepoImpl
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*

class SignUpUnitTest {
    @Mock
    private lateinit var mockAuth: FirebaseAuth

    @Mock
    private lateinit var mockTask: Task<AuthResult>

    @Mock
    private lateinit var mockUser: FirebaseUser

    private lateinit var signUpRepo: SignUpRepoImpl

    @Captor
    private lateinit var captor: ArgumentCaptor<OnCompleteListener<AuthResult>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        signUpRepo = SignUpRepoImpl(mockAuth)
    }

    @Test
    fun testSignup_Successful() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedMessage = "Initial Value"
        var expectedUid = ""

        // Mock Firebase behavior
        `when`(mockTask.isSuccessful).thenReturn(true)
        `when`(mockAuth.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        `when`(mockTask.addOnCompleteListener(any())).thenReturn(mockTask)
        `when`(mockAuth.currentUser).thenReturn(mockUser)
        `when`(mockUser.uid).thenReturn("testUID")

        // Define callback to capture result
        val callback = { success: Boolean, message: String, uid: String ->
            expectedMessage = message
            expectedUid = uid
        }

        // Call the function under test
        signUpRepo.signup(email, password, callback)

        // Capture and trigger completion
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        // Assertions
        assertEquals("Registration successful", expectedMessage)
        assertEquals("testUID", expectedUid)
    }

    @Test
    fun testSignup_Failed() {
        val email = "test@example.com"
        val password = "testPassword"
        var expectedMessage = "Initial Value"
        var expectedUid = ""

        // Mock Firebase behavior for failure
        `when`(mockTask.isSuccessful).thenReturn(false)
        `when`(mockTask.exception).thenReturn(Exception("Signup failed"))
        `when`(mockAuth.createUserWithEmailAndPassword(any(), any())).thenReturn(mockTask)
        `when`(mockTask.addOnCompleteListener(any())).thenReturn(mockTask)

        // Define callback to capture result
        val callback = { success: Boolean, message: String, uid: String ->
            expectedMessage = message
            expectedUid = uid
        }

        // Call the function under test
        signUpRepo.signup(email, password, callback)

        // Capture and trigger completion
        verify(mockTask).addOnCompleteListener(captor.capture())
        captor.value.onComplete(mockTask)

        // Assertions
        assertEquals("Signup failed", expectedMessage)
        assertEquals("", expectedUid) // UID should be empty on failure
    }
}