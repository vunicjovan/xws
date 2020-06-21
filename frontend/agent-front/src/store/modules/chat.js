import chatApi from "@/api/Chat.js";

const state = {
	messages: [],
};

const getters = {
	getChat: (state) => state.messages,
};

const actions = {
	getMessages({ commit }) {
		return new Promise((resolve, reject) => {
			chatApi
				.getMessages()
				.then((messages) => {
					commit("setMessages", messages);
					resolve();
				})
				.catch((error) => reject(error));
		});
	},

	sendMessage({ commit }, message) {
		return new Promise((resolve, reject) => {
			chatApi
				.sendMessage(message)
				.then((message) => {
					resolve(message);
				})
				.catch((error) => reject(error));
		});
	},
};

const mutations = {
	setMessages: (state, messages) => (state.messages = messages),
};

export default {
	state,
	getters,
	actions,
	mutations,
};
