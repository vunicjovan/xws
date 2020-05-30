import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/account/Login.vue";
import Register from "../views/account/Register.vue";
import Catalog from "../views/Catalog.vue";

Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		name: "Home",
		component: Home
	},
	{
		path: "/login",
		name: "Login",
		component: Login
	},
	{
		path: "/register",
		name: "Register",
		component: Register
	},
	{
		path: "/catalog",
		name: "Catalog",
		component: Catalog
	}
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes,
});

export default router;
