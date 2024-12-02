<template>
  <div id="app">
    <main>
      <router-view />
    </main>
  </div>
</template>

<script>
import {computed, ref} from 'vue';

export default {
  name: 'App'
};
</script>