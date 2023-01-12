import axios, {default as Axios} from "axios";
import router from "@/router";

const axiosInstance = Axios.create()

axiosInstance.interceptors.request.use(
    (config: any) => {
        const token = localStorage.getItem("access_token");
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`
        }
        return config;
    },
    (error: any) => {
        return Promise.reject(error);
    }
)

axiosInstance.interceptors.response.use(
    (response: any) => response,
    async (error: any) => {
        const config = error?.config;

        if (error?.response?.status === 401 && !config?.sent) {
            if (error?.response?.data.cause.causeExceptionName === "UserIsLockedException") {
                return Promise.reject(error);
            }
            config.sent = true;

            const result = await refreshTokenFn();

            if (result?.accessToken) {
                config.headers = {
                    ...config.headers,
                    authorization: `Bearer ${result?.accessToken}`,
                };
            }

            return axios(config);
        }
        return Promise.reject(error);
    }
)

const refreshTokenFn = async () => {
    const refreshToken = localStorage.getItem("refresh_token");
    const username = localStorage.getItem("username");

    const refreshTokenRequest = {
        refreshToken: refreshToken,
        username: username,
    }

    try {
        const response = await axios.post("/api/v1/token/refresh", refreshTokenRequest);
        const session = response.data;
        if (!session?.accessToken || !session?.role) {
            localStorage.removeItem("refresh_token");
            localStorage.removeItem("access_token");
            localStorage.removeItem("username");
            localStorage.removeItem("role")
            router.push("/login");
        }
        localStorage.setItem("role", session.role);
        localStorage.setItem("access_token", session.accessToken);
        router.go(0);

        return session;
    } catch (error) {
        localStorage.removeItem("access_token");
        localStorage.removeItem("refresh_token");
        localStorage.removeItem("username");
        localStorage.removeItem("role")
        router.push("/login");
    }
};

export default axiosInstance;