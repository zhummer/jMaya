import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HostC } from './host-c.model';
import { HostCPopupService } from './host-c-popup.service';
import { HostCService } from './host-c.service';

@Component({
    selector: 'jhi-host-c-dialog',
    templateUrl: './host-c-dialog.component.html'
})
export class HostCDialogComponent implements OnInit {

    host: HostC;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private hostService: HostCService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.host.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hostService.update(this.host));
        } else {
            this.subscribeToSaveResponse(
                this.hostService.create(this.host));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HostC>>) {
        result.subscribe((res: HttpResponse<HostC>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HostC) {
        this.eventManager.broadcast({ name: 'hostListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-host-c-popup',
    template: ''
})
export class HostCPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hostPopupService: HostCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hostPopupService
                    .open(HostCDialogComponent as Component, params['id']);
            } else {
                this.hostPopupService
                    .open(HostCDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
