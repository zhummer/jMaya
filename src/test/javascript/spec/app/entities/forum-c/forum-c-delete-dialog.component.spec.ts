/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JMayaTestModule } from '../../../test.module';
import { ForumCDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/forum-c/forum-c-delete-dialog.component';
import { ForumCService } from '../../../../../../main/webapp/app/entities/forum-c/forum-c.service';

describe('Component Tests', () => {

    describe('ForumC Management Delete Component', () => {
        let comp: ForumCDeleteDialogComponent;
        let fixture: ComponentFixture<ForumCDeleteDialogComponent>;
        let service: ForumCService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JMayaTestModule],
                declarations: [ForumCDeleteDialogComponent],
                providers: [
                    ForumCService
                ]
            })
            .overrideTemplate(ForumCDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ForumCDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ForumCService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
