import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/account/Login.vue";
import Register from "../views/account/Register.vue";
import Catalog from "../views/Catalog.vue";
import AdvertismentForm from "../components/forms/AdvertismentForm.vue";
import DetailedView from "../views/advertisement/DetailedView.vue";

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
	},
	{
		path: "/post-ad",
		name: "AdvertismentForm",
		component: AdvertismentForm
	},
	{
		path: "/single-ad/:id",
		name: "DetailedView",
		component: DetailedView
	}
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes,
});

export default router;
