import axios from "axios";

export default {
    getUserRentingRequests(userId) {
        return axios.get(`/rent/request/user/${userId}`).then((response) => {
            return response.data;
        })
    },

    updateRentingRequest(request) {
        return axios.put(`/rent/request/${request.id}`, request).then((response) => {
            return response.data;
        })
    },

    deleteRentingRequest(requestId) {
        return axios.delete(`rent/request/${requestId}`).then((response) => {
            return response.data;
        });
    }
};
