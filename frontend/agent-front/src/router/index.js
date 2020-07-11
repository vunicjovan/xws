import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import PostAdvertisement from "../forms/PostAdvertisement.vue";
import RequestsFinished from "../views/RequestsFinished.vue";
import Chat from "../views/Chat.vue";
import EditAdvertisement from "../views/advertisement/EditAdvertisement.vue";
import PriceList from "../components/PriceList.vue";
import StatisticReport from "../components/Statistic.vue";
import PendingRequests from "../views/renting/PendingRequests.vue";

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
	{
		path: "/edit/:id",
		name: "EditAdvertisement",
		component: EditAdvertisement,
	},
	{
		path: "/pricelist",
		name: "PriceList",
		component: PriceList,
	},
	{
		path: "/statistics",
		name: "StatisticReport",
		component: StatisticReport,
	},
	{
		path: "/requests/pending",
		name: "PendingRequests",
		component: PendingRequests,
	},
];

const router = new VueRouter({
	mode: "history",
	base: process.env.BASE_URL,
	routes,
});

export default router;
