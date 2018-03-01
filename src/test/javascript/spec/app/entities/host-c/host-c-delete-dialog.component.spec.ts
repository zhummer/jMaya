/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { JMayaTestModule } from '../../../test.module';
import { HostCDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/host-c/host-c-delete-dialog.component';
import { HostCService } from '../../../../../../main/webapp/app/entities/host-c/host-c.service';

describe('Component Tests', () => {

    describe('HostC Management Delete Component', () => {
        let comp: HostCDeleteDialogComponent;
        let fixture: ComponentFixture<HostCDeleteDialogComponent>;
        let service: HostCService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JMayaTestModule],
                declarations: [HostCDeleteDialogComponent],
                providers: [
                    HostCService
                ]
            })
            .overrideTemplate(HostCDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HostCDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HostCService);
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
