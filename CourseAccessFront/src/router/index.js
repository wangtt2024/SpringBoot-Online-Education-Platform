import { createRouter, createWebHistory } from 'vue-router';
import StudentDashboard from '../views/StudentDashboard.vue';
import LecturerDashboard from '../views/LecturerDashboard.vue';
import AdminDashboard from '../views/AdminDashboard.vue';
import Login from '../views/Login.vue';

const routes = [
    {
        path: '/',
        redirect: '/login' // Redirect to login page initially
    },
    {
        path: '/login',
        name: 'Login',
        component: Login,
    },
    {
        path: '/student',
        name: 'Student',
        component: StudentDashboard,
        meta: { requiresAuth: true, requiresRole: 'STUDENT' }
    },
    {
        path: '/lecturer',
        name: 'Lecturer',
        component: LecturerDashboard,
        meta: { requiresAuth: true, requiresRole: 'LECTURER' }
    },
    {
        path: '/admin',
        name: 'Admin',
        component: AdminDashboard,
        meta: { requiresAuth: true, requiresRole: 'ADMIN' }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Navigation guards
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role'); // Function to decode token and get role

    if (to.meta.requiresAuth && !token) {
        next('/login');
    } else if (to.meta.requiresRole && role !== to.meta.requiresRole) {
        next('/login');
    } else {
        next();
    }
});

function getTokenRole(token) {
    if (!token) {
        return null;
    }
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        return payload.role;
    } catch (error) {
        console.error('Error decoding token:', error);
        return null;
    }
}

export default router;