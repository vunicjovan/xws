import axios from "axios";

export default {
    getAdStatistics(ownerId) {
        return axios.get(`/ad/${ownerId}/statistic`).then((response) => {
            return response.data;
        });
    }
};
