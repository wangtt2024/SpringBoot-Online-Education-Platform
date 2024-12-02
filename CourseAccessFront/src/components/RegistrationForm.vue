<template>
  <h2>Registration Form</h2>
  <form @submit.prevent="$emit('submit', newuser)">
    <div>
      <label for="userid"> ID: </label>
      <input id="userid" v-model="newuser.id" placeholder="User-ID" required style="margin-right: 20px;">
      <label for="role"> Role: </label>
      <select v-model="newuser.role" placehorder="Student" style="margin-right: 20px;">
        <option value="STUDENT">Student</option>
        <option value="LECTURER">Lecturer</option>
      </select>
      <label v-if="newuser.role === 'STUDENT'" for="degree"> Degree: </label>
      <input v-if="newuser.role === 'STUDENT'" type="text" id="degree" v-model="newuser.degree">
      <label v-if="newuser.role === 'LECTURER'" for="specialization"> Specialization: </label>
      <input v-if="newuser.role === 'LECTURER'" type="text" id="specialization" v-model="newuser.specialization">
    </div>
    <div>
      <label for="nickname"> Nickname: </label>
      <input id="nickname" v-model="newuser.nickname" placehodler="Nickname">
    </div>
    <div>
      <label for="username"> New Username: </label>
      <input id="username" v-model="newuser.username" placeholder="Username" required>
    </div>
    <div>
      <label for="password"> New Password: </label>
      <input v-model="newuser.password" placeholder="Password" required>
    </div>
    <button type="submit">Register</button>
  </form>
</template>

<script setup>
import { ref, defineEmits, defineProps } from 'vue';

const newuser = ref({
  id: null,
  role: null,
  degree: null,
  specialization: null,
  nickname: null,
  username: null,
  password: null
});

const emit = defineEmits(['submit']);
</script>