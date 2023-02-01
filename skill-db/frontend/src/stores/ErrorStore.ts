import {defineStore} from "pinia";
import type {AxiosError} from "axios";
import {useAuthStore} from "@/stores/auth";

export const useErrorStore = defineStore(
    'ErrorStore',
    {
        state: () => ({
            hasError: Boolean(false),
            allowed: Boolean(false),
            errorText: '',
            authStore: useAuthStore(),
            errorMessages: {
                unknownError: 'Unbekannter Fehler!',
                unauthorized: 'Nicht autorisiert! Loggen Sie sich erneut ein.',
                internalServerError: 'Unbekannter Fehler aufgetreten. Bitte kontaktieren Sie Ihren Administrator, falls der Fehler anhält!',
                idNotFound: 'Id konnte nicht aufgelöst werden!',
                notAllowed: 'Sie haben keine Berechtigung, diese Funktion aufzurufen. Loggen Sie sich erneut ein.',
                mailNotSent: 'Mail wurde nicht gesendet! Deine Änderungen wurden dennoch gespeichert.'
            }
        }),
        actions: {

            toggleHasError() {
                this.hasError = false;
                this.errorText = '';
            },

            catchCustomAxios(error: AxiosError) {
                this.hasError = true;
                if (error.code === '202') {
                    this.errorText = error.message;
                    this.allowed = true;
                } else {
                    this.errorText = this.errorMessages.unknownError;
                }
            },

            catchError (error: Error) {
                this.hasError = true;
                this.errorText = error == undefined ? this.errorMessages.unknownError : error.message;
            },

            catchAllAxiosErrors(error: AxiosError, source: string, id: string) {
                this.hasError = true;
                if(error.response === undefined) {
                    this.handleUndefined(error, source);
                } else {
                    if (error.response.status === 400) {
                        this.handle400(error, source);
                    } else if (error.response.status === 401) {
                        this.handle401(error);
                    } else if (error.response.status == 403) {
                        this.handle403();
                    } else if (error.response.status === 404) {
                        this.handle404(error, source, id);
                    } else if (error.response.status === 409) {
                        this.handle409(error, source);
                    } else if (error.response.status === 500) {
                        this.handle500();
                    } else if (error.response.status === 502) {
                        this.handle502(error, source);
                    } else {
                        this.handleUndefined(error, source);
                    }
                }
            },

            handleUndefined(error: AxiosError, source:string) {
                this.errorText = this.errorMessages.unknownError;
                if (source === 'Import' && error.response?.data.message.contains('Mapping')) {
                    const colNameNotFound = `${error.response?.data.message
                        .toString()
                        .split(",")[0]
                        .split(' ')[2]}`
                    this.errorText = `Spalte \"${colNameNotFound}\" wurde in der CSV Datei nicht gefunden!`;
                }
            },

            handle400(error: AxiosError, source: string) {
                if (source === 'GetProfile') {
                    this.handle400forProfile(error); //Profile
                } else {
                    this.errorText = this.errorMessages.idNotFound;
                }
            },

            handle400forProfile(error: AxiosError) {
                const message: string = error.response?.data.message.toString();
                if (message === 'Der Nutzer hat bereits ein Profil!') {
                    this.errorText = message;
                } else if (message.includes('VALIDATION')) {
                    this.errorText = message.split(/(\[|\])/)[2];
                } else if (message === 'Datum nicht in passendem Format!') {
                    this.errorText = message;
                } else {
                    this.errorText = this.errorMessages.unknownError;
                }
            },

            handle401(error: AxiosError) {
                if (error.response?.data.cause.causeExceptionName === "UserIsLockedException") {
                    this.errorText = error.response?.data.message.toString();
                } else {
                    this.authStore.logout();
                    this.errorText = this.errorMessages.unauthorized;
                }
            },

            handle403() {
                this.authStore.logout();
                this.errorText = this.errorMessages.notAllowed;
            },

            handle404(error: AxiosError, source: string, id: string) {
                if (source === 'GetRoleId') {
                    this.errorText = `Rolle ${id} existiert nicht!`; //get Role ID
                } else if (source === 'GetId' || source === 'Delete') {
                    this.errorText = `Profil ${id} existiert nicht!`; //get ID
                } else if (source === 'GetProfileId') {
                    this.errorText = `Nutzer ${id} existiert nicht!`; //get Profile ID
                } else if (source === 'PostPatch' || source == 'Import') {
                    this.errorText = `${error.response?.data.message}`; //post patch
                } else {
                    this.errorText = this.errorMessages.unknownError;
                }
            },

            handle409(error: AxiosError, source: string) {
                if (source === 'PostPatch' || source === 'Import') {
                    this.errorText = 'Der Nutzer hat bereits ein Profil!'; //post patch
                } else {
                    this.errorText = this.errorMessages.unknownError;
                }
            },

            handle500() {
                this.errorText = this.errorMessages.internalServerError;
            },

            handle502(error: AxiosError, source: string) {
                if (source === 'Delete') {
                    this.errorText = this.errorMessages.mailNotSent; //delete ID
                } else if (source === 'PostPatch' || source === 'Import') {
                    this.errorText = this.errorMessages.mailNotSent; //post patch
                    this.allowed = true; //post patch
                } else {
                    this.errorText = this.errorMessages.unknownError;
                }
            },
        }
    }
)
