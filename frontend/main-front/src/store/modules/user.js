import accountApi from "@/api/Account";

const state = {
	logged: false,
};

const getters = {
	isLogged: (state) => state.logged,
};

const actions = {
	register({ commit }, user) {
		return new Promise((resolve, reject) => {
			accountApi
				.register(user)
				.then(() => {
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	login({ commit }, user) {
		return new Promise((resolve, reject) => {
			accountApi
				.login(user)
				.then(() => {
					commit("setLogged", true);
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	logout({ commit }) {
		localStorage.removeItem("auth");
		commit("setLogged", false);
	},

	setLogged({ commit }, condition) {
		commit("setLogged", condition);
	},
};

const mutations = {
	setLogged: (state, condition) => (state.logged = condition),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
