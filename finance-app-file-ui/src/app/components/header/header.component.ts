import { Component } from '@angular/core';
import { DropdownComponent } from '../../lib/components/dropdown/dropdown.component';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    imports: [
        DropdownComponent,
    ],
    standalone: true,
})
export class HeaderComponent {

}
