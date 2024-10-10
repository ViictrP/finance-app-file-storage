import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { DatePipe } from '@angular/common';

declare var duoIcons: {
    createIcons: Function;
};

@Component({
  selector: 'app-root',
  standalone: true,
    imports: [RouterOutlet, DatePipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
    title = 'Storage Management';
    today = new Date();

    ngOnInit(): void {
        if (typeof duoIcons !== 'undefined') {
            duoIcons.createIcons();
        }
    }
}
