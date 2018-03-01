import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ForumC } from './forum-c.model';
import { ForumCPopupService } from './forum-c-popup.service';
import { ForumCService } from './forum-c.service';

@Component({
    selector: 'jhi-forum-c-dialog',
    templateUrl: './forum-c-dialog.component.html'
})
export class ForumCDialogComponent implements OnInit {

    forum: ForumC;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private forumService: ForumCService,
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
        if (this.forum.id !== undefined) {
            this.subscribeToSaveResponse(
                this.forumService.update(this.forum));
        } else {
            this.subscribeToSaveResponse(
                this.forumService.create(this.forum));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ForumC>>) {
        result.subscribe((res: HttpResponse<ForumC>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ForumC) {
        this.eventManager.broadcast({ name: 'forumListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-forum-c-popup',
    template: ''
})
export class ForumCPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private forumPopupService: ForumCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.forumPopupService
                    .open(ForumCDialogComponent as Component, params['id']);
            } else {
                this.forumPopupService
                    .open(ForumCDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
