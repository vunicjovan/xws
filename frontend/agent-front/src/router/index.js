import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import PostAdvertisement from "../forms/PostAdvertisement.vue";
import RequestsFinished from "../views/RequestsFinished.vue";
import Chat from "../views/Chat.vue";

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
	{
		path: "/chat",
		name: "Chat",
		component: Chat,
	},
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes,
});

export default router;
