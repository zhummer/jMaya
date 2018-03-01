import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HostC } from './host-c.model';
import { HostCService } from './host-c.service';

@Component({
    selector: 'jhi-host-c-detail',
    templateUrl: './host-c-detail.component.html'
})
export class HostCDetailComponent implements OnInit, OnDestroy {

    host: HostC;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hostService: HostCService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHosts();
    }

    load(id) {
        this.hostService.find(id)
            .subscribe((hostResponse: HttpResponse<HostC>) => {
                this.host = hostResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHosts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hostListModification',
            (response) => this.load(this.host.id)
        );
    }
}
