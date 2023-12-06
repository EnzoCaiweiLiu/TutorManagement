<template>
    <el-container class="layout-container-demo" style="height: 95vh">
      <el-aside
          :style="isCollapse ? 'width: 80px'
          : 'width: 200px'"
      >
        <el-scrollbar>
          <el-button class="collapse-button" @click="isCollapse = !isCollapse" flat>
            <el-icon v-if="isCollapse"><ArrowRightBold /></el-icon>
            <el-icon v-else><ArrowLeftBold /></el-icon>
          </el-button>
          <el-menu
              :default-active="activeMenuItem"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
              :default-openeds="['1']"
              @select="handleSelect"
              :collapse="isCollapse"
          >
            <el-sub-menu index="1" v-if="isStudent">
              <template #title>
                <el-icon><Avatar /></el-icon>
                <span>Student</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="1-6">Course List</el-menu-item>
                <el-menu-item index="1-1">My Appointment</el-menu-item>
                <el-menu-item index="1-2">My Timetable</el-menu-item>
                <el-menu-item index="1-3">My Learning history</el-menu-item>
                <el-menu-item index="1-4">Support</el-menu-item>
              </el-menu-item-group>
              <el-sub-menu index="1-5">
                <template #title>Feedback</template>
                <el-menu-item index="1-5-1">Submit a feedback</el-menu-item>
                <el-menu-item index="1-5-2">View feedback history</el-menu-item>
              </el-sub-menu>
            </el-sub-menu>

            <el-sub-menu index="2" v-if="isTutor">
              <template #title>
                <el-icon><UserFilled /></el-icon>
                <span>Tutor</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="2-2">Appointment Request</el-menu-item>
                <el-menu-item index="2-3">My Courses</el-menu-item>
                <el-menu-item index="2-4">My Teaching history</el-menu-item>
                <el-menu-item index="2-5">Support</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>

            <el-sub-menu index="3" v-if="isAdmin">
              <template #title>
                <el-icon><setting /></el-icon>
                <span>Admin</span>
              </template>
              <el-sub-menu index="3-1">
                <template #title>Account Management</template>
                <el-menu-item index="3-1-1">Student Management</el-menu-item>
                <el-menu-item index="3-1-2">Tutor Management</el-menu-item>
                <el-menu-item index="3-1-3">Admin Management</el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="3-2">
                <template #title>Support Management</template>
                <el-menu-item index="3-2-1">All Queries</el-menu-item>
              </el-sub-menu>
              <el-sub-menu index="3-3">
                <template #title>Course Management</template>
                <el-menu-item index="3-3-1">Course Type</el-menu-item>
              </el-sub-menu>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 12px">
          <div class="toolbar">
            <el-dropdown class="custom-dropdown">
              <el-avatar style="border-style: inset;">{{ username }}</el-avatar>
              <template #dropdown>
                <el-dropdown-menu class="el-dropdown-menu">
                  <el-dropdown-item>
                    <router-link to="/">
                      Home
                    </router-link>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <router-link :to="{ name: 'Account', params: { userType: usertype, userId: userId } }">
                      Account Setting
                    </router-link>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <router-link :to="{ name: 'Message', params: { userType: usertype, userId: userId } }">
                    Message
                    </router-link>
                  </el-dropdown-item>
                    <el-dropdown-item>
                        <router-link to="/login">
                            Log out
                        </router-link>
                    </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>

        <el-main>
          <el-scrollbar>
            <template v-if="activeMenuItem === '1-6'">
              <TableCard :usertype="usertype" :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '1-2'">
              <CourseTimeTableCard :usertype="usertype" :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '1-3'">
              <LearningHistory :usertype="usertype" :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '1-1'">
              <MyAppointment :usertype="usertype" :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '1-4'">
              <SupportForm />
            </template>
            <template v-else-if="activeMenuItem === '1-5-1'">
              <FeedbackSubmitter :usertype="usertype" :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '1-5-2'">
              <FeedbackViewer :usertype="usertype" :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '2-2'">
              <TutAppointment :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '2-3'">
              <TutTimeTableCard :uid="Number(userId)"/>
            </template>
            <template v-else-if="activeMenuItem === '2-4'">
              <TutLearningHistory :usertype="usertype" :uid="Number(userId)" />
            </template>
            <template v-else-if="activeMenuItem === '2-5'">
              <SupportForm />
            </template>
            <template v-else-if="activeMenuItem === '3-1-1'">
              <StuManageCard />
            </template>
            <template v-else-if="activeMenuItem === '3-1-2'">
              <TutManageCard />
            </template>
            <template v-else-if="activeMenuItem === '3-1-3'">
              <AdmiManageCard />
            </template>
            <template v-else-if="activeMenuItem === '3-2-1'">
              <sup-manage-card />
            </template>
            <template v-else-if="activeMenuItem === '3-3-1'">
              <CourseMngCard />
            </template>
            <el-empty v-else :description="getEmptyDescription()" />
          </el-scrollbar>
        </el-main>
      </el-container>

    </el-container>
  </template>


<script lang="ts">
import { defineComponent } from 'vue';
import TableCard from './courseCard.vue';
import CourseTimeTableCard from './timetable.vue';
import TutTimeTableCard from './tutTime.vue';
import LearningHistory from './LearningHistory.vue';
import FeedbackSubmitter from "@/components/FBsubmitter.vue";
import FeedbackViewer from "@/components/FBviewer.vue"

export default defineComponent({
  name: 'MenuComponent',
  props: {
    username: String,
    isStudent: Boolean,
    isTutor: Boolean,
    isAdmin: Boolean,
    usertype: String,
    userId: String,
  },
  components: {
    TableCard,
    CourseTimeTableCard,
    TutTimeTableCard,
    LearningHistory,
    FeedbackSubmitter,
    FeedbackViewer,
  },
});
</script>

<script lang="ts" setup>
import {
    onMounted,
    onUnmounted,
    watch,
    ref,
    computed,
    defineProps,
} from 'vue';
import {
  UserFilled,
  Avatar,
  Setting,
  ArrowLeftBold,
  ArrowRightBold,
} from '@element-plus/icons-vue'
import MyAppointment from "@/components/MyAppointment.vue";
import SupportForm from "@/components/supportForm.vue";
import TutAppointment from "@/components/tutAppointment.vue";
import StuManageCard from "@/components/stuMngCard.vue";
import TutManageCard from "@/components/tutMngCard.vue";
import AdmiManageCard from "@/components/admMngCard.vue";
import TutLearningHistory from "@/components/tutLearningHistory.vue";
import SupManageCard from "@/components/supMngCard.vue";
import CourseMngCard from "@/components/courseMngCard.vue";

const windowWidth = ref(window.innerWidth);
const isCollapse = ref(windowWidth.value < 600);

const props = defineProps({
  username: String,
  isStudent: Boolean,
  isTutor: Boolean,
  isAdmin: Boolean,
  usertype: String,
  userId: String,
});

const defaultMenuItem = computed(() => {
  switch (props.usertype) {
    case 'student': return '1-6';
    case 'tutor': return '2-2';
    case 'admin': return '3-1-1';
    default: return '1-6';
  }
});
const activeMenuItem = ref(defaultMenuItem.value);

const updateWindowWidth = () => {
  windowWidth.value = window.innerWidth;
};

onMounted(() => {
  window.addEventListener('resize', updateWindowWidth);
});

onUnmounted(() => {
  window.removeEventListener('resize', updateWindowWidth);
});

watch(windowWidth, (newWidth) => {
  isCollapse.value = newWidth < 600;
});

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}

const handleSelect = (index: string) => {
  activeMenuItem.value = index

}
const getEmptyDescription = () => {
  switch (activeMenuItem.value) {
    case '1-1': return 'My Appointment'
    case '1-2': return 'My Timetable'
    case '1-3': return 'My Learning history'
    default: return ''
  }
}
</script>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background: linear-gradient(to right, #32be8f30, #4941ec, #6C63FF30);
  color: var(--el-text-color-primary);
}
.layout-container-demo .el-menu {
  border-right: none;
}
.layout-container-demo .el-main {
  padding: 0;
}
.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}

a{
  display: block;
  text-align: right;
  text-decoration: none;
  color: #999;
  font-size: 0.9rem;
  transition: .3s;
  font-family: 'Poppins', sans-serif ;
}

a:hover{
  color: #38d39f;
}

.collapse-button {
  width: 100%;
  border: none;
  background-color: transparent;
  color: #a8adb5;
  margin-bottom: 1rem;
}
</style>