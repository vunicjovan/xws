import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "../views/account/Login.vue";
import Register from "../views/account/Register.vue";
import Catalog from "../views/Catalog.vue";
import AdvertismentForm from "../components/forms/AdvertismentForm.vue";
import DetailedView from "../views/advertisement/DetailedView.vue";
import Cart from "../views/Cart.vue";
import SimpleUsers from "../views/SimpleUsers.vue";
import Chat from "../views/chat/Chat.vue";
import UnpublishedComments from "../components/comments/UnpublishedComments.vue";
import ChangePassword from "../views/account/ChangePassword.vue"
import RegistrationRequests from "../views/account/RegistrationRequests.vue";
import PublishedView from "../views/advertisement/PublishedView.vue";
import RentingRequest from "../views/RentingRequest.vue";
import RentingHistoryView from "../views/advertisement/RentingHistoryView.vue";
import StatisticReport from "../views/statistic/StatisticReport.vue";
import AccountActivation from "../views/account/AccountActivation.vue";

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
	},
	{
		path: "/cart",
		name: "Cart",
		component: Cart
	},
	{
		path: "/users",
		name: "SimpleUsers",
		component: SimpleUsers
	},
	{
		path: "/chat",
		name: "Chat",
		component: Chat
	},
	{
		path: "/unpublishedComments",
		name: "UnpublishedComments",
		component: UnpublishedComments
	},
	{
		path: "/password",
		name: "ChangePassword",
		component: ChangePassword
	},
	{
		path: "/registrationRequests",
		name: "RegistrationRequests",
		component: RegistrationRequests
	},
	{
		path: "/ads/published",
		name: "PublishedView",
		component: PublishedView
	},
	{
		path: "/ads/published/request/:id",
		name: "RentingRequest",
		component: RentingRequest
	},
	{
		path: "/ads/rented",
		name: "RentingHistoryView",
		component: RentingHistoryView
	},
	{
		path: "/statistic/:id",
		name: "StatisticReport",
		component: StatisticReport
	},
	{
		path: "/registerUser/:token",
		name: "AccountActivation",
		component: AccountActivation
	},
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes,
});

export default router;
