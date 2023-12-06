<template>
  <div class="dashboard4All">
    <MenuComponent
        :username="username"
        :isStudent="isStudent"
        :isTutor="isTutor"
        :isAdmin="isAdmin"
        :usertype="usertype"
        :userId="userId"
    />
  </div>
</template>

<script lang="ts">
import MenuComponent from '@/components/indexDash.vue';

import { defineComponent, computed } from 'vue';
import { useRoute } from 'vue-router';

export default defineComponent({
  name: "DashboardPage",
  components: {
    MenuComponent,
  },
  setup() {
    const route = useRoute();

    return {
      username: "User",
      isStudent: computed(() => route.params.userType === 'student'),
      isTutor: computed(() => route.params.userType === 'tutor'),
      isAdmin: computed(() => route.params.userType === 'admin'),
      usertype: computed(() => route.params.userType),
      userId: computed(() => route.params.userId)
    };
  }
});
</script>
