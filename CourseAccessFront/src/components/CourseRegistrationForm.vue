<template>
  <h2>Course Registration Form</h2>
  <form @submit.prevent="$emit('submit', newcourse)">
    <div>
      <label for="id"> Course ID: </label>
      <input id="id" v-model="newcourse.id" placeholder="Course-ID" required style="margin-right: 20px;">
      <label for="name"> Course Name: </label>
      <input id="name" v-model="newcourse.name" placeholder="Course Name" required style="margin-right: 20px;">
      <label for="capacity"> Capacity: </label>
      <input id="capacity" v-model="newcourse.capacity" placeholder="Capacity" required style="margin-right: 20px;">
      <label for="lecturer"> Lecturer ID: </label>
      <input id="lecturer" v-model="newcourse.lecturer.id" placeholder="Lecturer-ID" required style="margin-right: 20px;">
    </div>
    <div>
      <label for="description"> Description: </label>
      <textarea id="description" v-model="newcourse.description" placeholder="Description" rows="1" cols="150"></textarea>
    </div>
    <button type="submit">Register</button>
    <button type="button" @click="handleDelete">Delete</button>
  </form>
</template>

<script setup>
import { ref } from 'vue';

const newcourse = ref({
  id: '',
  name: '',
  description: '',
  lecturer: { id: '' },
  capacity: ''
});
</script>

<style scoped>
</style>