import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { EmplLeaveTypeDetailComponent } from 'app/entities/empl-leave-type/empl-leave-type-detail.component';
import { EmplLeaveType } from 'app/shared/model/empl-leave-type.model';

describe('Component Tests', () => {
  describe('EmplLeaveType Management Detail Component', () => {
    let comp: EmplLeaveTypeDetailComponent;
    let fixture: ComponentFixture<EmplLeaveTypeDetailComponent>;
    const route = ({ data: of({ emplLeaveType: new EmplLeaveType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [EmplLeaveTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(EmplLeaveTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EmplLeaveTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load emplLeaveType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.emplLeaveType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
