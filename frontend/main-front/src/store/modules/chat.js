import chatApi from "@/api/Chat.js";

const state = {
    messages: [],
};

const getters = {
    getChat: (state) => state.messages,
};

const actions = {
    getMessages({ commit }, userId) {
        return new Promise((resolve, reject) => {
            chatApi
				.getMessages(userId)
				.then((messages) => {
					commit("setMessages", messages);
					resolve();
				})
				.catch((error) => reject(error));
        });
    }
};

const mutations = {
    setMessages: (state, messages) => (state.messages = messages),
}

export default {
    state,
    getters,
    actions,
    mutations
}
