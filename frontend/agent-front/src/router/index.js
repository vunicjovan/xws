import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import PostAdvertisement from "../forms/PostAdvertisement.vue";
import RequestsFinished from "../views/RequestsFinished.vue";

Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		name: "Home",
		component: Home,
	},
	{
		path: "/postAd",
		name: "PostAdvertisement",
		component: PostAdvertisement,
	},
	{
		path: "/requestsFinished",
		name: "RentingFinished",
		component: RequestsFinished,
	},
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes,
});

export default router;
