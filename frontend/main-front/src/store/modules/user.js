import accountApi from "@/api/Account";

const state = {
	logged: false,
	loggedUser: null,
};

const getters = {
	isLogged: (state) => state.logged,
	getUser: (state) => state.loggedUser,
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
		commit("setLoggedUser", null);
	},

	setLogged({ commit }, condition) {
		commit("setLogged", condition);
	},

	logged({ commit }) {
		return new Promise((resolve, reject) => {
			accountApi
				.logged()
				.then((user) => {
					commit("setLoggedUser", user);
					resolve();
				})
				.catch((error) => {
					localStorage.removeItem("auth");
					commit("setLoggedUser", null);
					commit("setLogged", false);
					reject(error);
				});
		});
	},

	activateAccount({ commit }, token) {
		return new Promise((resolve, reject) => {
			accountApi
				.activateAccount(token)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	changePassword({ commit }, passwordSet) {
		return new Promise((resolve, reject) => {
			accountApi
				.changePassword(passwordSet)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	createResetToken({ commit }, email) {
		return new Promise((resolve, reject) => {
			accountApi
				.createResetToken(email)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	resetPassword({ commit }, tokenDTO) {
		return new Promise((resolve, reject) => {
			accountApi
				.resetPassword(tokenDTO)
				.then((data) => {
					resolve(data);
				})
				.catch((error) => {
					reject(error);
				});
		});
	},

	updateUser({ commit }, updateDTO) {
		return new Promise((resolve, reject) => {
			accountApi
				.updateUser(updateDTO)
				.then((user) => {
					commit("setLoggedUser", user);
					resolve();
				})
				.catch((error) => {
					reject(error);
				});
		});
	},
};

const mutations = {
	setLogged: (state, condition) => (state.logged = condition),
	setLoggedUser: (state, user) => (state.loggedUser = user),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
