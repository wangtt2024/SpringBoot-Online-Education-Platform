\<template>
  <div class="login-container login-background" >
    <div class="login-box">
      <h1>Login</h1>
      <form @submit.prevent="handleLogin">
        <label for="username">Username:</label>
        <input type="text" id="username" v-model="username">
        <label for="password">Password:</label>
        <input type="password" id="password" v-model="password">
        <label for="role">Role:</label>
        <select id="role" v-model="selectedRole">
          <option value="STUDENT">Student</option>
          <option value="LECTURER">Lecturer</option>
          <option value="ADMIN">Admin</option>
        </select>
        <button type="submit">Login</button>
      </form>
      <p v-if="errorMessage">{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed} from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const username = ref('');
const password = ref('');
const selectedRole = ref('STUDENT'); // Default role
const errorMessage = ref(null);

const baseUrl = 'http://localhost:8080/api';
const userUrl = computed(() => {
  switch (selectedRole.value) {
    case 'STUDENT':
      return `${baseUrl}/student/user`;
    case 'LECTURER':
      return `${baseUrl}/lecturer/user`;
    default:
      return null;
  }
});

const handleLogin = async () => {
  try {
    const response = await axios.post(`${baseUrl}/auth/login`, {
      username: username.value,
      password: password.value,
      role: selectedRole.value
    });
    const token = response.data.token;
    if (!token) {
      errorMessage.value = 'Login failed'
    } else {
      localStorage.setItem('token', token);
      localStorage.setItem('role', selectedRole.value);
      localStorage.setItem('username', username.value);
      if (selectedRole.value !== 'ADMIN') {
        const userIdResponse = await axios.get(`${userUrl.value}/find/id/${username.value}`);
        console.log('request:', `${userUrl.value}/find/id/${username.value}`);
        console.log('lecture find all response:', userIdResponse);
        const userId = userIdResponse.data;
        localStorage.setItem('userId', userId);
      } else {
        localStorage.setItem('role', selectedRole.value);
      }

      errorMessage.value = null;

      // Redirect based on role
      if (selectedRole.value === 'STUDENT') {
        router.push('/student');
      } else if (selectedRole.value === 'LECTURER') {
        router.push('/lecturer');
      } else if (selectedRole.value === 'ADMIN') {
        router.push('/admin');
      }
    }
  } catch (error) {
    errorMessage.value = error.response?.data?.message || 'Login failed';
    console.error('Login error:', error);
  }
};
</script>

<style>
body {
  font-family: Arial, sans-serif;
}

.login-background {
  background: url('../assets/books1.jpg') no-repeat center center fixed;
  background-size: cover;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-box {
  background: rgba(255, 255, 255, 0.8);
  padding: 20px;
  border-radius: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align:left;
  width: 500px;
}

.login-box h1 {
  margin-bottom: 20px;
}

.login-box label {
  display: block;
  margin: 10px 0 5px;

}

.login-box input,
.login-box select {
  width: 95%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;

}

.login-box button {
  width: 100%;
  padding: 10px;
  background: #007bff;
  border: none;
  border-radius: 5px;
  color: white;
  font-size: 16px;
  cursor: pointer;
}

.login-box button:hover {
  background: #0056b3;
}

.login-box p {
  color: red;
}
</style>
