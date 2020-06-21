import axios from "axios";

export default {
    getAdStatistics(adId) {
        return axios.get(`/ad/${adId}/statistic`).then((response) => {
            return response.data;
        });
    }
};
