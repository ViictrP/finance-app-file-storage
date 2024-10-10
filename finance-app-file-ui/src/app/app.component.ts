import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DatePipe } from '@angular/common';
import { DrawerComponent } from './components/drawer/drawer.component';
import { HeaderComponent } from './components/header/header.component';

declare const duoIcons: {
    createIcons: Function;
};

@Component({
  selector: 'app-root',
  standalone: true,
    imports: [RouterOutlet, DatePipe, DrawerComponent, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
    title = 'Storage Management';

    ngOnInit(): void {
        if (typeof duoIcons !== 'undefined') {
            duoIcons.createIcons();
        }
    }
}
