import { Component, Input } from '@angular/core';

@Component({
    selector: 'app-dropdown',
    templateUrl: './dropdown.component.html',
    standalone: true,
})
export class DropdownComponent {

    @Input({ required: true }) title!: string;
}
