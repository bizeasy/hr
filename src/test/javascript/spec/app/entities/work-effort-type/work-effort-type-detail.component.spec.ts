import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { WorkEffortTypeDetailComponent } from 'app/entities/work-effort-type/work-effort-type-detail.component';
import { WorkEffortType } from 'app/shared/model/work-effort-type.model';

describe('Component Tests', () => {
  describe('WorkEffortType Management Detail Component', () => {
    let comp: WorkEffortTypeDetailComponent;
    let fixture: ComponentFixture<WorkEffortTypeDetailComponent>;
    const route = ({ data: of({ workEffortType: new WorkEffortType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [WorkEffortTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(WorkEffortTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkEffortTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load workEffortType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workEffortType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
