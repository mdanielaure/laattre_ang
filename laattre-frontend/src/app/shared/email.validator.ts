import { AbstractControl } from "@angular/forms";

export function EmailValidator (control: AbstractControl): {[key: string]: any} | null {
    const EMAIL_PATTERN = /[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$/;
    const validEmail = EMAIL_PATTERN.test(control.value);
    return !validEmail ? {'invalidEmail': {value: control.value}} : null;
}