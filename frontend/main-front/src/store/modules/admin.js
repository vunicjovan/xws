import adminApi from "@/api/Admin.js"

const state = {
    simpleUsers: null,
    registrationRequests: null
};

const getters = {
    getSimpleUsers: (state) => state.simpleUsers,
    getRegistrationRequests: (state) => state.registrationRequests
};

const actions = {

    getSimpleUsers({commit}) {
        return new Promise((resolve, reject) => {
            adminApi
                .getSimpleUsers()
                .then((simpleUsers) => {
                    commit("setSimpleUsers", simpleUsers);
                    resolve(simpleUsers);
                })
                .catch((error) => reject(error))
        })
    },

    blockUser({commit}, userId) {
        return new Promise((resolve, reject) => {
            adminApi
                .blockUser(userId)
                .then((simpleUser) => {
                    commit("blockUser", simpleUser);
                    resolve(simpleUser);
                })
                .catch((error) => reject(error))
        })
    },

    deleteUser({commit}, userId) {
        return new Promise((resolve, reject) => {
            adminApi
                .deleteUser(userId)
                .then((simpleUser) => {
                    commit("deleteSimpleUser", userId);
                    resolve(simpleUser);
                })
                .catch((error) => reject(error))
        })
    },

    getRegistrationRequests({commit}) {
        return new Promise((resolve, reject) => {
            adminApi
                .getUnregisteredAgents()
                .then((registrationRequests) => {
                    commit("setRegistrationRequests", registrationRequests);
                    resolve(registrationRequests);
                })
                .catch((error) => reject(error))
        });
    },

    sendRegistrationResponse({commit}, registrationResponse) {
        return new Promise((resolve, reject) => {
            adminApi
                .sendRegistrationResponse(registrationResponse)
                .then((registrationResponse) => {
                    commit("setRegistrationResponse", registrationResponse.id);
                    resolve();
                })
                .catch((error) => reject(error))
        });
    }

};

const mutations = {
    setSimpleUsers: (state, simpleUsers) => (state.simpleUsers = simpleUsers),
    blockUser: (state, simpleUser) => {
        state.simpleUsers.forEach(element => {
                if (element.id === simpleUser.id) {
                    element.blocked = simpleUser.blocked;
                }
        });
    },
    deleteSimpleUser: (state, simpleUserId) => {
        state.simpleUsers = state.simpleUsers.filter((simpleUser) => simpleUser.id != simpleUserId);
    },
    setRegistrationRequests: (state, registrationRequests) => (state.registrationRequests = registrationRequests),
    setRegistrationResponse: (state, id) => {
		state.registrationRequests = state.registrationRequests.filter((registrationRequest) => registrationRequest.id != id);
	},
};

export default {
    state,
    getters,
    actions,
    mutations
}
