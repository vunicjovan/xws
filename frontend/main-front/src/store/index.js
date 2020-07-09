import Vuex from "vuex";
import Vue from "vue";
import user from "./modules/user.js";
import catalog from "./modules/catalog.js";
import advertisment from "./modules/advertisment.js";
import search from "./modules/search.js";
import cart from "./modules/cart.js";
import admin from "./modules/admin.js";
import renting from "./modules/rentingIntervals.js";
import comment from "./modules/comment.js";
import rentingRequest from "./modules/rentingRequest.js";
import rentingHistory from "./modules/rentingHistory.js";
import chat from "./modules/chat.js";
import pricelist from "./modules/pricelist.js";
import debt from "./modules/debt.js";

Vue.use(Vuex);

export default new Vuex.Store({
	modules: {
		user,
		catalog,
		advertisment,
		search,
		cart,
		admin,
		renting,
		comment,
		rentingRequest,
		rentingHistory,
		chat,
		pricelist,
		debt,
	},
});
