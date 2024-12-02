<!-- LogoutButton.vue -->
<template>
<button @click="$emit('logout')">Logout</button>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['logout']);
</script>