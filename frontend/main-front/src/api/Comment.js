import axios from "axios"

export default {
    getUnpublishedComments() {
		return axios.get("/agent/ad/comment/unapproved/").then((response) => {
			return response.data;
		});
    },
    
    publishComment(id, comId) {
		return axios.put(`/agent/ad/${id}/comment/${comId}`).then((response) => {
			return response.data;
		});
    },
    
    rejectComment(adId, id) {
		return axios.delete(`/agent/ad/${adId}/comment/${id}`).then((response) => {
			return response.data;
		});
	},
	
	postComment(comment) {
		return axios.post(`/agent/ad/comment`, comment).then((response) => {
			return response.data;
		});
	},

	postAgentComment(comment) {
		return axios.post(`/agent/ad/agent/comment`, comment).then((response) => {
			return response.data;
		});
	},
}
