<template>
    <LogoutButton @logout="handleLogout" />
    <div class="background">
        <div class="dashboard-container">
            <h1 class="centered">Admin Dashboard</h1>
            <RegistrationForm @submit="handleUserRegistration" />
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import axios from 'axios';
import RegistrationForm from '../components/RegistrationForm.vue';
import LogoutButton from '../components/LogoutButton.vue';

const userUrl = 'http://localhost:8080/api/admin/user';
const courseUrl = 'http://localhost:8080/api/admin/course';
const moduleUrl = 'http://localhost:8080/api/admin/module';
const subscriptionUrl = 'http://localhost:8080/api/admin/subscription';

const handleUserRegistration = async (newuser) => {
    try {
        console.log(newuser);
        const userRegisterUrl = computed(() => {
            switch (newuser.role) {
                case 'STUDENT':
                    return `${userUrl}/register/student`;
                case 'LECTURER':
                    return `${userUrl}/register/lecturer`;
                default:
                    return null;
            }
        });
        const newRegisterUser = computed(() => {
            switch (newuser.role) {
                case 'STUDENT':
                    return {
                        id: newuser.id,
                        username: newuser.username,
                        password: newuser.password,
                        nickname: newuser.nickname,
                        degree: newuser.degree
                    };
                case 'LECTURER':
                    return {
                        id: newuser.id,
                        username: newuser.username,
                        password: newuser.password,
                        nickname: newuser.nickname,
                        specialization: newuser.specialization
                    };
                default:
                    return null;
            }
        });
        console.log('User registration request', userRegisterUrl.value, newRegisterUser.value);
        const response = await axios.post(userRegisterUrl.value, newRegisterUser.value);
        console.log('User registration response:', response.data);
    } catch (error) {
        console.error('Error registering user:', error);
    }
}

const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    localStorage.removeItem('userId');
    window.location.href = '/login';
};

</script>