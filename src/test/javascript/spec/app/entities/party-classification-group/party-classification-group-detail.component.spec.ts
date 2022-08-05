import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrTestModule } from '../../../test.module';
import { PartyClassificationGroupDetailComponent } from 'app/entities/party-classification-group/party-classification-group-detail.component';
import { PartyClassificationGroup } from 'app/shared/model/party-classification-group.model';

describe('Component Tests', () => {
  describe('PartyClassificationGroup Management Detail Component', () => {
    let comp: PartyClassificationGroupDetailComponent;
    let fixture: ComponentFixture<PartyClassificationGroupDetailComponent>;
    const route = ({ data: of({ partyClassificationGroup: new PartyClassificationGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HrTestModule],
        declarations: [PartyClassificationGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PartyClassificationGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartyClassificationGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load partyClassificationGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partyClassificationGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
