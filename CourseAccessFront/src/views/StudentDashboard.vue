<template>
  <LogoutButton @logout="handleLogout" />
  <div class="background">
    <div class="dashboard-container">
      <h1 class="centered">Student Dashboard</h1>
      <div class="module">
        <h2>Student Info</h2>
        <button @click="toggleStudentInfo">{{ showStudentInfo ? 'Hide My Info' : 'Show My Info' }}</button>
        <div v-if="showStudentInfo && foundStudent" class="student-info">
          <h3>Student Information</h3>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Nickname</th>
                <th>Enable</th>
                <th>Update Time</th>
                <th>Create Time</th>
                <th>Degree</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{{ foundStudent.id }}</td>
                <td>{{ foundStudent.username }}</td>
                <td>{{ foundStudent.nickname }}</td>
                <td>{{ foundStudent.enable }}</td>
                <td>{{ foundStudent.updateTime }}</td>
                <td>{{ foundStudent.createTime }}</td>
                <td>{{ foundStudent.degree }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div class="module">
        <h2>My Courses</h2>
        <h3>Show All My Registered Courses</h3>
        <button @click="ToggleCourses">{{ showCourses ? 'Hide My Courses' : 'Show My Courses' }}</button>
        <div v-if="showCourses && foundCourses" class="student-info">
          <h3>My Courses</h3>
          <table>
            <thead>
              <tr>
                <th>Course ID</th>
                <th>Course Name</th>
                <th>Description</th>
                <th>Lecturer Name</th>
                <th>Action</th>
                <th>Rate</th>
                <th>Comment</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="course in foundCourses" :key="course.courseid">
                <td>{{ course.courseid }}</td>
                <td>{{ course.coursename }}</td>
                <td>{{ course.description }}</td>
                <td>{{ course.lecturername }}</td>
                <td><button @click="dropCourse(course.courseid)">Drop Course</button></td>
                <td>
                  <input type="number" v-model="course.ratingScore" min="1" max="5" placeholder="Score (1-5)">
                  <button @click="rateCourse(course.courseid, course.ratingScore)">Add Rating</button>
                </td>
                <td>
                  <input type="text" v-model="course.ratingComment" placeholder="Add Comment">
                  <button @click="commentCourse(course.courseid, course.ratingComment)">Add Comment</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <h3>Show All Courses</h3>
        <button @click="ToggleAllCourses">{{ showAllCourses ? 'Hide All Courses' : 'Show All Courses' }}</button>
        <div v-if="showAllCourses && foundAllCourses" class="student-info">
          <h3>All Courses</h3>
          <table>
            <thead>
              <tr>
                <th>Course ID</th>
                <th>Course Name</th>
                <th>Description</th>
                <th>Lecturer Name</th>
                <th>Capacity</th>
                <th>Details</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="course in foundAllCourses" :key="course.courseid">
                <td>{{ course.courseid }}</td>
                <td>{{ course.coursename }}</td>
                <td>{{ course.description }}</td>
                <td>{{ course.lecturername }}</td>
                <td>{{ course.capacity }}</td>

                <td><button @click="seeModules(course.courseid)">See Modules</button></td>
                <td><button @click="addCourse(course.courseid)">Add Course</button></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>


    <!-- Display modules -->
    <div class="module-outside-container" v-if="foundModules.length > 0">
      <h3>Details for {{ selectedCourse }}</h3>
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
    </div>

    <div class="bottom">
      <h2>Delete My Account</h2>
      <button @click="deleteMyself">Delete</button>
    </div>

    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import LogoutButton from '../components/LogoutButton.vue';

const baseUrl = 'http://localhost:8080/api/student/user'; // CHANGE THIS TO YOUR BACKEND URL
const SubscriptionUrl = 'http://localhost:8080/api/student/subscription';
const CourseUrl = 'http://localhost:8080/api/student/course';
const ModuleUrl = 'http://localhost:8080/api/student/module';
const current_user = ref(localStorage.getItem('username'));
const current_user_id = ref(localStorage.getItem('userId') || '');

const foundStudent = ref(null);
const showStudentInfo = ref(false);

const foundCourses = ref([]);
const showCourses = ref(false);

const foundAllCourses = ref([]);
const showAllCourses = ref(false);

const successMessage = ref(null);
const errorMessage = ref(null);

const foundModules = ref([]);
const selectedCourse = ref('');


const registerStudent = async (student) => {
  try {
    await axios.post(`${baseUrl}/register`, student);
    successMessage.value = 'Student registered successfully!';
    errorMessage.value = null;
  } catch (error) {
    handleError(error);
  }
};

const deleteMyself = async () => {
  try {
    await axios.delete(`${baseUrl}/delete/${current_user.value}`);
    errorMessage.value = 'Account deleted successfully!';
    successMessage.value = null;
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    window.location.href = '/login';
  } catch (error) {
    handleError(error);
  }
};

const findStudent = async () => {
  try {
    const response = await axios.get(`${baseUrl}/find/${current_user.value}`);
    foundStudent.value = response.data;
    errorMessage.value = null;
  } catch (error) {
    handleError(error);
  }
};

const toggleStudentInfo = async () => {
  if (!showStudentInfo.value) {
    await findStudent();
  }
  showStudentInfo.value = !showStudentInfo.value;
};


const handleError = (error, context = 'operation') => {
  errorMessage.value = `Error during ${context}: ${error.message}`;
  successMessage.value = null;
};

const findMyCourses = async () => {
  try {
    console.log('student_id:', current_user_id.value);
    const response = await axios.get(`${SubscriptionUrl}/find/all`, {
      params: {
        student_id: current_user_id.value
      }
    });
    foundCourses.value = response.data.map(subscription => ({
      courseid: subscription.course.id,
      coursename: subscription.course.name,
      description: subscription.course.description,
      lecturername: subscription.course.lecturer.username
    }));
    errorMessage.value = null;
  } catch (error) {
    handleError(error, 'finding courses');
  }
};

const ToggleCourses = async () => {
  if (!showCourses.value) {
    await findMyCourses();
  }
  showCourses.value = !showCourses.value;
};

const findAllCourses = async () => {
  try {
    const response = await axios.get(`${CourseUrl}/find/all`);
    console.log('response:', response);
    foundAllCourses.value = response.data.map(course => ({
      courseid: course.id,
      coursename: course.name,
      description: course.description,
      lecturername: course.lecturer.username,
      capacity: course.capacity
    }));
    errorMessage.value = null;
  } catch (error) {
    handleError(error, 'finding all courses');
  }
};

const ToggleAllCourses = async () => {
  if (!showAllCourses.value) {
    await findAllCourses();
  }
  showAllCourses.value = !showAllCourses.value;
};

const addCourse = async (courseId) => {
  try {
    const response = await axios.post(`${SubscriptionUrl}/add/${courseId}`, null, {
      params: {
        student_id: current_user_id.value
      }
    });
    console.log('response:', response);
    const addedCourse = response.data.course.id;
    successMessage.value = 'Course added successfully!';
    errorMessage.value = null;
    foundCourses.value.push({
      courseid: courseId,
      coursename: response.data.course.name,
      description: response.data.course.description,
      lecturername: response.data.course.lecturer.username
    });
  } catch (error) {
    handleError(error, 'adding course');
  }
};

const dropCourse = async (courseId) => {
  try {
    await axios.delete(`${SubscriptionUrl}/delete/${courseId}`, {
      params: {
        student_id: current_user_id.value
      }
    });
    successMessage.value = 'Course dropped successfully!';
    errorMessage.value = null;
    foundCourses.value = foundCourses.value.filter(course => course.courseid !== courseId);
  } catch (error) {
    handleError(error, 'dropping course');
  }
};

const rateCourse = async (courseId, courseRating) => {
  try {
    await axios.post(`${SubscriptionUrl}/rate/${courseId}`, null, {
      params: {
        student_id: current_user_id.value,
        ratingScore: courseRating
      }
    });
  }
  catch (error) {
    handleError(error, 'rating course');
  }
}

const commentCourse = async (courseId, courseComments) => {
  try {
    console.log('courseComments:', courseComments);
    await axios.post(`${SubscriptionUrl}/comment/${courseId}`, null, {
      params: {
        student_id: current_user_id.value,
        ratingComment: courseComments
      }
    });
  }
  catch (error) {
    handleError(error, 'commenting course');
  }
}

const seeModules = async (courseId) => {
  try {
    const response = await axios.get(`${ModuleUrl}/find/all/${courseId}`);
    console.log('response:', response);

    foundModules.value = response.data.map(module => ({
      id: module.id,
      chapter: module.chapter,
      title: module.title,
      summary: module.summary,
      courseName: module.course.name
    }));
    selectedCourse.value = foundModules.value.length > 0 ? foundModules.value[0].courseName : '';
    errorMessage.value = null;
  } catch (error) {
    handleError(error, 'viewing modules');
  }
};


const handleLogout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  localStorage.removeItem('username');
  localStorage.removeItem('userId');
  window.location.href = '/login';
};
</script>

<style>
.background {
  background-color: #f5deb3;
  /* 米黄色背景 */
  padding: 20px;
}

.dashboard-container {
  background: url('../assets/student_reg.jpg') no-repeat center center;
  /* 插入图片 */
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
  background-color: rgba(255, 255, 255, 0.7);
  /* 半透明白色背景 */
  border-radius: 10px;
  z-index: 1;
}

.dashboard-container>* {
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

.student-info {
  margin-top: 20px;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  background: #f9f9f9;
}

.student-info table {
  width: 100%;
  border-collapse: collapse;
}

.student-info th,
.student-info td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.student-info th {
  background-color: #f2f2f2;
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