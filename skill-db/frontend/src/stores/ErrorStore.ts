import {defineStore} from "pinia";
import type {AxiosError} from "axios";
import {useAuthStore} from "@/stores/auth";

export const useErrorStore = defineStore(
    'ErrorStore',
    {
        state: () => ({
            hasError: Boolean(false),
            errorText: '',
            authStore: useAuthStore(),
            errorMessages: {
                unknownError: 'Unbekannter Fehler!',
                unauthorized: 'Nicht autorisiert! Loggen Sie sich erneut ein.',
                internalServerError: 'Unbekannter Fehler aufgetreten. Bitte kontaktieren Sie Ihren Administrator, falls der Fehler anhält!',
                idNotFound: 'Profil Id konnte nicht aufgelöst werden!',
                notAllowed: 'Sie haben keine Berechtigung, diese Funktion aufzurufen. Loggen Sie sich erneut ein.',
                mailNotSent: 'Mail wurde nicht gesendet! Deine Änderungen wurden dennoch gespeichert.'
            }
        }),
        actions: {

            toggleHasError() {
                this.hasError = false;
                this.errorText = '';
            },

            catchGetAllError(error: AxiosError) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = this.errorMessages.unknownError;
                } else {
                    if (error.response.status === 401) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.unauthorized;
                    } else if (error.response.status === 403) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.notAllowed;
                    } else if (error.response.status === 500) {
                        this.errorText = this.errorMessages.internalServerError;
                    } else {
                        this.errorText = this.errorMessages.unknownError;
                    }
                }
            },

            catchImportError(error: AxiosError) {
                this.catchPostPatchError(error);
                if (this.errorText === this.errorMessages.unknownError) {
                    this.errorText = error.response.data.message.toString().split(",")[0];
                }
            },

            catchPostPatchError(error: AxiosError) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = this.errorMessages.unknownError;
                } else {
                    if (error.response.status === 400) {
                        this.handle400forProfile(error);
                    } else if (error.response.status === 401) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.unauthorized;
                    } else if (error.response.status === 500) {
                        this.errorText = this.errorMessages.internalServerError;
                    } else if (error.response.status === 403) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.notAllowed;
                    } else if (error.response.status === 404) {
                        this.errorText = `${error.response.data.message}`;
                    } else if (error.response.status === 502) {
                        this.errorText = this.errorMessages.mailNotSent;
                    } else {
                        this.errorText = this.errorMessages.unknownError;
                    }
                }
            },

            handle400forProfile(error: AxiosError) {
                const message: string = error.response.data.message.toString();
                if (message === 'Der Nutzer hat bereits ein Profil!') {
                    this.errorText = message;
                } else if (message.includes('VALIDATION')) {
                    let innerMessage = message.split(/(\[|\])/)[2];
                    this.errorText = innerMessage;
                } else if (message === 'Datum nicht in passendem Format!') {
                    this.errorText = message;
                } else {
                    this.errorText = this.errorMessages.unknownError;
                }
            },

            catchSkillsJobsPrimariesError(error: AxiosError, name: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = this.errorMessages.unknownError;
                } else {
                    this.errorText += ` ${name} wurde nicht korrekt vom Server erhalten!`
                }
            },

            catchUploadImageError(error: Error) {
                this.hasError = true;
                this.errorText = error == undefined ? this.errorMessages.unknownError : error.message;
            },

            catchDownloadImageError(error: AxiosError) {
                this.hasError = true;
                this.errorText = this.errorMessages.unknownError
            },

            catchGetError(error: AxiosError, id: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = this.errorMessages.unknownError;
                } else {
                    if (error.response.status === 404) {
                        this.errorText = `Profil ${id} existiert nicht!`;
                    } else if (error.response.status === 401) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.unauthorized;
                    } else if (error.response.status === 500) {
                        this.errorText = this.errorMessages.internalServerError;
                    } else if (error.response.status === 400) {
                        this.errorText = this.errorMessages.idNotFound;
                    }
                }
            },

            catchDeleteError(error: AxiosError, id: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = this.errorMessages.unknownError;
                } else {
                    if (error.response.status === 404) {
                            this.errorText = `Profil ${id} existiert nicht!`;
                    } else if (error.response.status === 401) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.unauthorized;
                    } else if (error.response.status === 403) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.notAllowed;
                    } else if (error.response.status === 500) {
                        this.errorText = this.errorMessages.internalServerError;
                    } else if (error.response.status === 400) {
                        this.errorText = this.errorMessages.idNotFound;
                    } else if (error.response.status === 502) {
                        this.errorText = this.errorMessages.mailNotSent;
                    } else {
                        this.errorText = this.errorMessages.unknownError;
                    }
                }
            },

            catchExportError(error: AxiosError) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = this.errorMessages.unknownError;
                } else {
                    if (error.response.status === 403) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.notAllowed;
                    } else if (error.response.status === 500) {
                        this.errorText = this.errorMessages.internalServerError;
                    } else {
                        this.errorText = this.errorMessages.unknownError;
                    }
                }
            },

            catchGetRoleError(error: AxiosError, id: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = this.errorMessages.unknownError;
                } else {
                    if (error.response.status === 404) {
                        this.errorText = `Rolle ${id} existiert nicht!`;
                    } else if (error.response.status === 401) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.unauthorized;
                    } else if (error.response.status === 403) {
                        this.authStore.logout();
                        this.errorText = this.errorMessages.notAllowed;
                    } else if (error.response.status === 500) {
                        this.errorText = this.errorMessages.internalServerError;
                    } else if (error.response.status === 400) {
                        this.errorText = 'Rollen Id konnte nicht aufgelöst werden!'
                    }
                }
            },

        }
    }
)
