/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { JMayaTestModule } from '../../../test.module';
import { HostCDetailComponent } from '../../../../../../main/webapp/app/entities/host-c/host-c-detail.component';
import { HostCService } from '../../../../../../main/webapp/app/entities/host-c/host-c.service';
import { HostC } from '../../../../../../main/webapp/app/entities/host-c/host-c.model';

describe('Component Tests', () => {

    describe('HostC Management Detail Component', () => {
        let comp: HostCDetailComponent;
        let fixture: ComponentFixture<HostCDetailComponent>;
        let service: HostCService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JMayaTestModule],
                declarations: [HostCDetailComponent],
                providers: [
                    HostCService
                ]
            })
            .overrideTemplate(HostCDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HostCDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HostCService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HostC(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.host).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
