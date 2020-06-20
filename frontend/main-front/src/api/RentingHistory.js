import axios from "axios";

export default {
    getUserRentingHistory(userId) {
        return axios.get(`/rent/history/${userId}`).then((response) => {
            return response.data;
        })
    }
}