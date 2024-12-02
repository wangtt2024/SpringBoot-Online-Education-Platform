<template>
  <div>
    <LogoutButton @logout="handleLogout" />
    <div class="background">
      <div class="dashboard-container">
        <h1 class="centered">Lecturer Dashboard</h1>
        <div class="lecturer-view">
          <h2>Lecturer Info</h2>
          <button @click="toggleLecturerInfo">Show Lecturer Info</button>
          <div v-if="showLecturerInfo">
            <h3>Lecturer Information</h3>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Username</th>
                  <th>Nickname</th>
                  <th>Enable</th>
                  <th>Update Time</th>
                  <th>Create Time</th>
                  <th>Specialization</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{{ foundLecturer.id }}</td>
                  <td>{{ foundLecturer.username }}</td>
                  <td>{{ foundLecturer.nickname }}</td>
                  <td>{{ foundLecturer.enable }}</td>
                  <td>{{ foundLecturer.updateTime }}</td>
                  <td>{{ foundLecturer.createTime }}</td>
                  <td>{{ foundLecturer.specialization }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <div class="lecturer-view">
          <h2>My Courses</h2>
          <button @click="toggleMyCourses">Show My Courses</button>
          <div v-if="showMyCourses">
            <h3>Course Information</h3>
            <table>
              <thead>
                <tr>
                  <th>Course ID</th>
                  <th>Course Name</th>
                  <th>Capacity</th>
                  <th>My Rating</th>
                  <th>Comments</th>
                  <th>Modules</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="course in foundCourses" :key="course.courseid">
                  <td>{{ course.courseid }}</td>
                  <td>{{ course.coursename }}</td>
                  <td>{{ course.capacity }}</td>
                  <td>{{ course.averageRating }}</td>
                  <td>
                    <button @click="toggleComments(course.courseid)">
                      Show Comments
                    </button>
                  </td>
                  <td>
                    <button @click="toggleModules(course.courseid)">
                      Show Modules
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
            <div v-if="showComments">
              <h3>Comments</h3>
              <ul>
                <li v-for="comment in courseComments" :key="comment">{{ comment }}</li>
              </ul>
              <button @click="toggleCommentsVisibility">Hide Comments</button>
            </div>

            <div v-if="showModules">
              <h3>Modules</h3>
              <table>
                <thead>
                  <tr>
                    <th>Chapter</th>
                    <th>Title</th>
                    <th>Summary</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="module in foundModules" :key="module.id">
                    <td>{{ module.chapter }}</td>
                    <td>{{ module.title }}</td>
                    <td>{{ module.summary }}</td>
                  </tr>
                </tbody>
              </table>
              <button @click="toggleModulesVisibility">Hide Modules</button>
            </div>
          </div>
        </div>

        <div class="lecturer-view">
          <div class="add-module">
            <h3>Add Module</h3>
            <form @submit.prevent="addModule">

              <label for="courseId">Course ID:</label>
              <input type="text" id="courseId" v-model="newModule.courseId" required>

              <label for="chapter">Chapter:</label>
              <input type="text" id="chapter" v-model="newModule.chapter" required>

              <label for="title">Title:</label>
              <input type="text" id="title" v-model="newModule.title" required>

              <label for="summary">Summary:</label>
              <input type="text" id="summary" v-model="newModule.summary" required>

              <button type="submit">Add Module</button>
            </form>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import LogoutButton from '../components/LogoutButton.vue';

export default {
  name: 'LecturerDashboard',
  components: {
    LogoutButton
  },
  setup() {
    const UserUrl = 'http://localhost:8080/api/lecturer/user';
    const CourseUrl = 'http://localhost:8080/api/lecturer/course';
    const SubscriptionsUrl = 'http://localhost:8080/api/lecturer/subscription';
    const ModuleUrl = 'http://localhost:8080/api/lecturer/module';
    const current_user = ref(localStorage.getItem('username'));
    const current_user_id = ref(localStorage.getItem('userId') || '');

    const foundLecturer = ref(null);
    const showLecturerInfo = ref(false);
    const foundCourses = ref([]);
    const showMyCourses = ref(false);

    const courseComments = ref([]);
    const showComments = ref(false);

    const foundModules = ref([]);
    const selectedCourseId = ref('');
    const showModules = ref(false);

    const newModule = ref({
      courseId: '',
      chapter: '',
      title: '',
      summary: ''
    });


    const courseId = ref('');
    const foundCourse = ref(null);
    const errorMessage = ref(null);

    const findLecturer = async () => {
      try {
        const response = await axios.get(`${UserUrl}/find/${current_user.value}`);
        console.log('Response:', response); // Log the response
        foundLecturer.value = response.data;
      } catch (error) {
        handleError(error);
      }
    };

    const toggleLecturerInfo = async () => {
      if (!showLecturerInfo.value) {
        await findLecturer();
      }
      showLecturerInfo.value = !showLecturerInfo.value;
    };

    const findMyCoursesWithRating = async () => {
      try {
        const response = await axios.get(`${CourseUrl}/find/average-rating`, {
          params: {
            lecturerId: current_user_id.value
          }
        });
        console.log('Response:', response);
        foundCourses.value = response.data.map(course => ({
          courseid: course.courseId,
          coursename: course.courseName,
          capacity: course.courseCapacity,
          averageRating: course.averageRating
        }));
      } catch (error) {
        console.error('Error:', error);
      }
    };

    const toggleMyCourses = async () => {
      if (!showMyCourses.value) {
        await findMyCoursesWithRating();
      }
      showMyCourses.value = !showMyCourses.value;
    };

    const handleError = (error) => {
      console.error('Error:', error); // Log the error
    };

    const findComments = async (courseId) => {
      try {
        const response = await axios.get(`${SubscriptionsUrl}/rating/${courseId}/comments`);
        console.log('Comment Response:', response.data);
        courseComments.value = response.data;
      } catch (error) {
        console.error('Error:', error);
      }
    };

    const toggleComments = async (courseId) => {
      await findComments(courseId);
      showComments.value = true;
    };

    const toggleCommentsVisibility = () => {

      showComments.value = !showComments.value;
    };

    const findModules = async (courseId) => {
      try {
        const response = await axios.get(`${ModuleUrl}/find/all/${courseId}`);
        console.log('Modules Response:', response.data);
        foundModules.value = response.data.map(module => ({
          chapter: module.chapter,
          title: module.title,
          summary: module.summary
        }));
      } catch (error) {
        console.error('Error:', error);
      }
    };

    const toggleModules = async (courseId) => {
      await findModules(courseId);
      showModules.value = true;
    };

    const toggleModulesVisibility = () => {
      showModules.value = !showModules.value;
    };

    const addModule = async () => {
      try {
        const response = await axios.put(`${ModuleUrl}/update`, {
          chapter: newModule.value.chapter,
          title: newModule.value.title,
          summary: newModule.value.summary,
          course: {id: newModule.value.courseId}
        });
        console.log('New Module Response:', response.data);
        // Handle the response, e.g., clear the form or show a success message
        newModule.value = {courseId: '', chapter: '', title: '', summary: ''};
        foundModules.value.push({
          chapter: response.data.chapter,
          title: response.data.title,
          summary: response.data.summary,
          course: {id: newModule.value.courseId}
        });
      } catch (error) {
        console.error('Error:', error);
      }
    };

    const handleLogout = () => {
      localStorage.removeItem('token');
      localStorage.removeItem('role');
      localStorage.removeItem('username');
      localStorage.removeItem('userId');
      window.location.href = '/login';
    };

    return {
      foundLecturer,
      showLecturerInfo,
      toggleLecturerInfo,
      findMyCoursesWithRating,
      foundCourses,
      showMyCourses,
      toggleMyCourses,
      showComments,
      toggleComments,
      courseComments,
      toggleCommentsVisibility,
      foundModules,
      showModules,
      findModules,
      toggleModules,
      toggleModulesVisibility,
      newModule,
      addModule,
      handleError,
      LogoutButton,
      handleLogout
    };
  }
}

</script>

<style>
body {
  background-color: #f5deb3; /* Light yellow background */
}

.background {
  background-color: #f5deb3; /* 米黄色背景 */
  padding: 20px;
}

.dashboard-container {
  background: url('../assets/lecturer.jpg') no-repeat center center; /* 插入图片 */
  background-size: cover;
  padding: 20px;
  border-radius: 10px;
  position: relative;
}

.dashboard-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border-radius: 10px;
  z-index: 1;
}

.dashboard-container > * {
  position: relative;
  z-index: 2;
}

.centered {
  text-align: center;
}

.module {
  margin-bottom: 20px;
}

.bottom {
  margin-top: 40px;
  text-align: center;
}

.lecturer-view {
  margin-top: 20px;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  background: transparent; /* Transparent background */
}

.lecturer-view table {
  width: 100%;
  border-collapse: collapse;
}

.lecturer-view th,
.lecturer-view td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
  background-color: #f2f2f2; /* Light grey background */
}

.lecturer-view th {
  background-color: #f2f2f2; /* Light grey background */
}

.module-outside-container table {
  width: 100%;
  border-collapse: collapse;
}

.module-outside-container th,
.module-outside-container td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.module-outside-container th {
  background-color: #f2f2f2;
}
</style>
