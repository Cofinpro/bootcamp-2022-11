import {defineStore} from "pinia";
import type {AxiosError} from "axios";

export const useErrorStore = defineStore(
    'ErrorStore',
    {state: () =>({
            hasError: Boolean(false),
            errorText: '',
        }),
        actions: {

            toggleHasError() {
                this.hasError = false;
                this.errorText= '';
            },

            catchOverviewError(error: AxiosError) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = 'Unbekannter Fehler!';
                } else {
                    if (error.response.status === 401) {
                        this.errorText = 'Nicht autorisiert!';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren Sie Ihren Administrator, falls der Fehler anhält!';
                    } else {
                        this.errorText = 'Unbekannter Fehler!'
                    }
                }
            },

            catchPostPatchError(error: AxiosError) {
                this.hasError=true;
                if (error.response == undefined) {
                    this.errorText = 'Unbekannter Fehler!';
                } else {
                    if (error.response.status === 400) {
                        this.handle400forProfile(error);
                    } else if (error.response.status === 401) {
                        this.errorText = 'Nicht autorisiert!';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren Sie Ihren Administrator, falls der Fehler anhält!';
                    } else if (error.response.status === 404) {
                        this.errorText = `404: ${error.response.data.message}`;
                    } else {
                        this.errorText = 'Unbekannter Fehler!';
                    }
                }
            },

            handle400forProfile(error: AxiosError) {
                const message: string = error.response.data.message.toString();
                if (message === 'Der zurzeit eingeloggte Nutzer hat bereits ein Profil!') {
                    this.errorText = message;
                } else if (message.includes('VALIDATION')) {
                    let innerMessage = message.split(/(\[])/)[2];
                    /*innerMessage = innerMessage.replaceAll(',', ' ');*/
                    this.errorText = innerMessage;
                } else if (message === 'Datum nicht in passendem Format!') {
                    this.errorText = message;
                } else {
                    this.errorText = 'Unbekannter Fehler!';
                }
            },

            catchSkillsJobsPrimariesError(error: AxiosError, name: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = 'Unbekannter Fehler!';
                } else {
                    this.errorText += ` ${name} wurde nicht korrekt vom Server erhalten!`
                }
            },

            catchGetError(error: AxiosError, id: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = 'Unbekannter Fehler!';
                } else {
                    if (error.response.status === 404) {
                        this.errorText = `Profil ${id} existiert nicht!`;
                    } else if (error.response.status === 401) {
                        this.errorText = 'Nicht autorisiert!';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren Sie Ihren Administrator, falls der Fehler anhält!';
                    } else if (error.response.status === 400) {
                        this.errorText = 'Profil Id konnte nicht aufgelöst werden!'
                    }
                }
            },

            catchDeleteError(error: AxiosError, id: String) {
                this.hasError = true;
                if (error.response == undefined) {
                    this.errorText = 'Unbekannter Fehler!';
                } else {
                    if (error.response.status === 404) {
                        this.errorText = `Profil ${id} existiert nicht!`;
                    } else if (error.response.status === 401) {
                        this.errorText = 'Recht zum Löschen dieses Profils nicht vorhanden!';
                    } else if (error.response.status === 500) {
                        this.errorText = 'Unbekannter Fehler aufgetreten. Bitte kontaktieren Sie Ihren Administrator, falls der Fehler anhält!';
                    } else if (error.response.status === 400) {
                        this.errorText = 'Profil Id konnte nicht aufgelöst werden!'
                    }
                }
            },
        }
    }
)
