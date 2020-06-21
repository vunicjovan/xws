import Vuex from "vuex";
import Vue from "vue";
import advertisement from "./modules/advertisement.js";
import catalog from "./modules/catalog.js";
import rentingIntervals from "./modules/rentingIntervals.js";
import renting from "./modules/renting.js";
import chat from "./modules/chat.js";

Vue.use(Vuex);

export default new Vuex.Store({
	modules: {
		advertisement,
		catalog,
		rentingIntervals,
		renting,
		chat,
	},
});
