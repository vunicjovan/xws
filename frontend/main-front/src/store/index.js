import Vuex from "vuex";
import Vue from "vue";
import user from "./modules/user.js";
import catalog from "./modules/catalog.js";
import advertisment from "./modules/advertisment.js";
import search from "./modules/search.js"
import cart from "./modules/cart.js"
import admin from "./modules/admin.js"

Vue.use(Vuex);

export default new Vuex.Store({
	modules: {
		user,
		catalog,
		advertisment,
		search,
		cart,
		admin
	},
});
