import commentApi from "@/api/Comment.js"

const state = {
    unpublishedComments: null
};

const getters = {
    getUnpublishedCommentList: (state) => state.unpublishedComments
};

const actions = {

    getUnpublishedComments({commit}) {
        return new Promise((resolve, reject) => {
            commentApi
                .getUnpublishedComments()
                .then((comments) => {
                    commit("setUnpublishedComments", comments);
                    //resolve(comments);
                })
                .catch((error) => reject(error))
        })
    },

    publishComment({commit}, comm) {
        return new Promise((resolve, reject) => {
            commentApi
                .publishComment(comm.advertisementId, comm.id)
                .then((comment) => {
                    commit("setCommentStatus", comm.id);
                    resolve();
                })
                .catch((error) => reject(error))
        })
    },

    rejectComment({commit}, comm) {
        return new Promise((resolve, reject) => {
            commentApi
                .rejectComment(comm.advertisementId, comm.id)
                .then((comment) => {
                    commit("setCommentStatus", comm.id);
                    resolve();
                })
                .catch((error) => reject(error))
        })
    },

    postComment({ commit }, comment) {
        return new Promise((resolve, reject) => {
            commentApi
                .postComment(comment)
                .then((comment) => {
                    resolve();
                })
                .catch((error) => reject(error));
        });
    }

};

const mutations = {
    setUnpublishedComments: (state, unpublishedComments) => (state.unpublishedComments = unpublishedComments),
    setCommentStatus: (state, commentId) => {
        state.unpublishedComments = state.unpublishedComments.filter((comment) => comment.id != commentId);
    },
};

export default {
    state,
    getters,
    actions,
    mutations
}
