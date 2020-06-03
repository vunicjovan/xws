import Vuex from "vuex";
import Vue from "vue";
import user from "./modules/user.js";
import catalog from "./modules/catalog.js";
import advertisment from "./modules/advertisment.js";
import search from "./modules/search.js"

Vue.use(Vuex);

export default new Vuex.Store({
	modules: {
		user,
		catalog,
		advertisment,
		search
	},
});
