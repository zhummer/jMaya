/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JMayaTestModule } from '../../../test.module';
import { HostCComponent } from '../../../../../../main/webapp/app/entities/host-c/host-c.component';
import { HostCService } from '../../../../../../main/webapp/app/entities/host-c/host-c.service';
import { HostC } from '../../../../../../main/webapp/app/entities/host-c/host-c.model';

describe('Component Tests', () => {

    describe('HostC Management Component', () => {
        let comp: HostCComponent;
        let fixture: ComponentFixture<HostCComponent>;
        let service: HostCService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [JMayaTestModule],
                declarations: [HostCComponent],
                providers: [
                    HostCService
                ]
            })
            .overrideTemplate(HostCComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HostCComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HostCService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HostC(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hosts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
