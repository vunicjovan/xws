import adminApi from "@/api/Admin.js"

const state = {
    simpleUsers: null
};

const getters = {
    getSimpleUsers: (state) => state.simpleUsers,
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
                    commit("deleteSimpleUser", simpleUser);
                    resolve(simpleUser);
                })
                .catch((error) => reject(error))
        })
    }

};

const mutations = {
    setSimpleUsers: (state, simpleUsers) => (state.simpleUsers = simpleUsers),
    blockUser: (state, simpleUser) => {
        state.simpleUsers.array.forEach(element => {
                if (element.id == simpleUser.id) {
                    element.blocked == true;
                }
        });
    },
    deleteSimpleUsers: (state, simpleUserId) => {
        state.simpleUsers = state.simpleUsers.filter((simpleUser) => simpleUser.id != simpleUserId);
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}
