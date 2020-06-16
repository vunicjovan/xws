import Vuex from "vuex";
import Vue from "vue";
import advertisement from "./modules/advertisement.js";
import catalog from "./modules/catalog.js";

Vue.use(Vuex);

export default new Vuex.Store({
	modules: {
		advertisement,
		catalog,
	},
});
