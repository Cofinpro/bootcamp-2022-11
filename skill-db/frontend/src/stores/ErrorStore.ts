import {defineStore} from "pinia";
import {AxiosError} from "axios";

export const useErrorStore = defineStore(
    'ErrorStore',
    {state: () =>({
            hasError: Boolean(false),
            errorText: String(),
        }),
        actions: {
            catchError(error: AxiosError){
                this.hasError=true;
                if (error.response == undefined) {
                    this.errorText ="Unknown error";
                } else {
                    if (error.response.status === 400) {
                        this.handle400(error)
                    } else if (error.response.status === 401) {
                        this.errorText = 'Unauthorized'
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unknown error occurred. Please contact your administrator!'
                    }
                    else if (error.response.status === 404) {
                        this.errorText = `404: ${error.response.data.message}`;
                    } else {
                        this.errorText = 'Unknown error'
                    }
                }
            },
            toggleHasError() {
                this.hasError = false;
                this.errorText= '';
            },
            handle400(error: AxiosError) {
                const message: String = String(error.response.data.message);
                if (message.includes('Cannot deserialize value of type `java.time.LocalDate`')) {
                    this.errorText= "Unbekanntes Format des Datums!"
                }
                else if (message === 'Der zurzeit eingelogte Nutzer hat bereits ein Profil!') {
                    this.errorText = message;
                }
                else if (message.includes('VALIDATION')) {
                    let innerMessage = message.split(/(\[|])/)[2];
                    innerMessage = innerMessage.replaceAll(',', ' ');
                    this.errorText = innerMessage;
                }

            },
        }
    }
)
